# Generated by Django 2.1.5 on 2019-02-07 18:49

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('logDbApp', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='logentry',
            name='stop',
        ),
    ]