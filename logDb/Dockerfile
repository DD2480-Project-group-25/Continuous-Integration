FROM python:3-alpine3.9

WORKDIR /logDB
COPY . .
RUN pip3 install -r requirements.txt

CMD sh -c "python3 manage.py makemigrations && python3 manage.py migrate --run-syncdb"
