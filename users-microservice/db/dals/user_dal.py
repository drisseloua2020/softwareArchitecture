from typing import List, Optional

from fastapi import HTTPException, Depends
from fastapi.security import OAuth2PasswordBearer
from sqlalchemy.future import select
from sqlalchemy.orm import Session

from db.models.user import User
from db.models.Role import Role
from db.models.Profile import Profile


from oauth_library import OAuth2Provider

class UserDAL():
    def __init__(self, db_session: Session):
        self.db_session = db_session

    async def create_user(self, name: str, email: str, mobile: str, password: str):
        new_user = User(name=name, email=email, mobile=mobile)
        new_user.set_password(password)
        self.db_session.add(new_user)
        await self.db_session.flush()

    async def get_all_users(self) -> List[User]:
        q = await self.db_session.execute(select(User).order_by(User.id))
        return q.scalars().all()

    async def get_user(self, user_id: int) -> User:
        q = await self.db_session.execute(select(User).where(User.id == user_id))
        return q.scalar()

    async def update_user(self, user_id: int, name: Optional[str] = None, email: Optional[str] = None, mobile: Optional[str] = None):
        q = update(User).where(User.id == user_id)
        if name:
            q = q.values(name=name)
        if email:
            q = q.values(email=email)
        if mobile:
            q = q.values(mobile=mobile)
        q.execution_options(synchronize_session="fetch")
        await self.db_session.execute(q)

    async def login_user(self, username: str, password: str):
        oauth_provider = OAuth2Provider()

        user = oauth_provider.authenticate_user(username, password)

        if user:

            return user
        else:
            raise HTTPException(status_code=401, detail="Invalid credentials")


# Example OAuth2PasswordBearer usage
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

def get_current_user(token: str = Depends(oauth2_scheme), user_dal: UserDAL = Depends(get_user_dal)):
    return user_dal.get_user_by_token(token)

async def authenticate_user(self, username: str, password: str) -> User:
    q = await self.db_session.execute(select(User).where(User.email == username))
    user = q.scalar()

    if user and user.verify_password(password):
        return user
    else:
        return None

async def get_user_roles(self, user_id: int) -> List[Role]:
    q = await self.db_session.execute(select(Role).join(User).where(User.id == user_id))
    return q.scalars().all()

async def get_user_profile(self, user_id: int) -> Profile:
    q = await self.db_session.execute(select(Profile).join(User).where(User.id == user_id))
    return q.scalar()

async def has_role(self, user_id: int, role_name: str) -> bool:
    roles = await self.get_user_roles(user_id)
    return any(role.name == role_name for role in roles)

async def has_permission(self, user_id: int, permission_name: str) -> bool:
    profile = await self.get_user_profile(user_id)
    # Assuming Profile has a list of permissions
    return permission_name in profile.permissions if profile else False
