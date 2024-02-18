provider "aws" {
  region = "us-east-1"
}

# Create S3 bucket
resource "aws_s3_bucket" "my_bucket" {
  bucket = "my-unique-s3-bucket"
  acl    = "private"
}

# Create CloudFront distribution
resource "aws_cloudfront_distribution" "my_distribution" {
  origin {
    domain_name = aws_s3_bucket.my_bucket.bucket_regional_domain_name
    origin_id   = "S3Origin"
  }

  enabled             = true
  is_ipv6_enabled     = true
  default_root_object = "index.html"

  # ... Other CloudFront configuration
}

# Create API Gateway
resource "aws_api_gateway_rest_api" "my_api" {
  name        = "my-api"
  description = "My API Gateway"
}

# Create Fargate cluster
resource "aws_ecs_cluster" "my_cluster" {
  name = "my-cluster"
}

# Create subnets
resource "aws_subnet" "public_subnet" {
  count             = 2
  cidr_block        = element(["10.0.1.0/24", "10.0.2.0/24"], count.index)
  availability_zone = element(["us-east-1a", "us-east-1b"], count.index)
  map_public_ip_on_launch = true
}

resource "aws_subnet" "private_subnet" {
  count             = 2
  cidr_block        = element(["10.0.3.0/24", "10.0.4.0/24"], count.index)
  availability_zone = element(["us-east-1a", "us-east-1b"], count.index)
}

# Create SQS queue
resource "aws_sqs_queue" "my_queue" {
  name = "my-queue"
}

# SQS policy for allowing API Gateway to send messages
resource "aws_sqs_queue_policy" "my_queue_policy" {
  queue_url = aws_sqs_queue.my_queue.id

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Id": "APIGateway-SQS-Policy",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": "*",
      "Action": "sqs:SendMessage",
      "Resource": "${aws_sqs_queue.my_queue.arn}",
      "Condition": {
        "ArnEquals": {
          "aws:SourceArn": "${aws_api_gateway_rest_api.my_api.execution_arn}/*"
        }
      }
    }
  ]
}
EOF
}

# Output the CloudFront distribution domain name
output "cloudfront_domain_name" {
  value = aws_cloudfront_distribution.my_distribution.domain_name
}