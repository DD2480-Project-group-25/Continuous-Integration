from django.db import models

class LogEntry(models.Model):
    commit_id = models.CharField(max_length=100, verbose_name='Commit ID', primary_key=True)
    start = models.CharField(max_length=100, verbose_name='Start')
    status = models.CharField(max_length=100, verbose_name='Status')
