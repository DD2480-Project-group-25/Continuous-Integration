import django_tables2 as tables
from .models import LogEntry

class LogEntryTable(tables.Table):
    class Meta:
        model = LogEntry
        template_name = 'django_tables2/bootstrap.html'