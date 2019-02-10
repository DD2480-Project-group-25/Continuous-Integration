import django_tables2 as tables
from .models import LogEntry
from django_tables2.utils import A

class LogEntryTable(tables.Table):
    detail = tables.LinkColumn('detail', text='Details', args=[A('pk')],
                         orderable=False, empty_values=())
    class Meta:
        model = LogEntry
        template_name = 'django_tables2/bootstrap.html'