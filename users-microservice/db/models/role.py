from sqlalchemy import Column, Integer, String
from passlib.hash import bcrypt

from db.config import Base

class Role(Base):
    __tablename__ = 'Role'

    id = Column(Integer, primary_key=True)
    permission = Column(String, nullable=False)
