from sqlalchemy import Column, Integer, String
from passlib.hash import bcrypt

from db.config import Base

from sqlalchemy import Column, Integer, DateTime, ForeignKey
from sqlalchemy.orm import relationship
from db.config import Base
from db.models.role import Role  # Assuming you have a Role model

class Profile(Base):
    __tablename__ = 'Profile'

    profileId = Column(Integer, primary_key=True)
    profileType = Column(DateTime, nullable=False)
    daysOfChange = Column(Integer, nullable=False)
    profileRole = Column(Integer, ForeignKey('Role.roleId'), nullable=False)

    role = relationship('Role', back_populates='profiles')

