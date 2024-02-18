from fastapi import FastAPI
import uvicorn
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.orm import sessionmaker

from db.config import engine, Base
from routers import user_router
from db.models.user import User
from db.utils import execute_sql_file

app = FastAPI()
app.include_router(user_router.router)

# Define the path to the SQL file containing user inserts
user_inserts_sql_file = "data/user_inserts.sql"


@app.on_event("startup")
async def startup():
    # create db tables
    async with engine.begin() as conn:
        await conn.run_sync(Base.metadata.drop_all)
        await conn.run_sync(Base.metadata.create_all)

    # Execute the SQL file to insert users
    async_session = sessionmaker(bind=engine, class_=AsyncSession)
    async with async_session() as session:
        await execute_sql_file(session, user_inserts_sql_file)


if __name__ == '__main__':
    uvicorn.run("app:app", port=9090, host='127.0.0.1', reload=True)
