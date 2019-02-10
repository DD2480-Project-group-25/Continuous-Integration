
from django.shortcuts import render
from django_tables2 import RequestConfig

from .models import LogEntry
from rest_framework.views import APIView
from rest_framework.response import Response
from .serializers import LogEntrySerializer
from .tables import LogEntryTable

def logs(request):
    logTable = LogEntryTable(LogEntry.objects.all())
    RequestConfig(request).configure(logTable)
    logTable.exclude= ('message',)
    return render(request, 'logDbApp/log.html', {'log': logTable})


def entryDetailView(request, b):

    commit = request.path.split("/")[2]
    return render(request, 'logDbApp/entryDetail.html',
                  {'Details': LogEntry.objects.filter(commit_id=commit)})


class logView(APIView):


    def get(self, request):
        logItems = LogEntry.objects.all()
        serializer = LogEntrySerializer(logItems, many=True)
        return Response({"log entries": serializer.data})

    def post(self, request):
        logItem = request.data.get('log entries')

        serializer = LogEntrySerializer(data=logItem)
        if serializer.is_valid():
            entry_saved = serializer.save()
        return Response({"success": "LogEntry '{}' created successfully".format(entry_saved).title})
