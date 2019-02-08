FROM python:3-alpine3.9

COPY . /app

RUN pip3 install -r app/requirements.txt

RUN python3 app/manage.py makemigrations
RUN python3 app/manage.py migrate
CMD python3 app/manage.py runserver 8080