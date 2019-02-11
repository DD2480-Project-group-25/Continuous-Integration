from django.db import models

class LogEntry(models.Model):
    """
    Model for main table.
    """
    commit_id = models.CharField(max_length=100, verbose_name='Commit ID', primary_key=True)
    start = models.CharField(max_length=100, verbose_name='Start')
    status = models.CharField(max_length=100, verbose_name='Status')
    message = models.CharField(max_length=200, verbose_name='Message')


