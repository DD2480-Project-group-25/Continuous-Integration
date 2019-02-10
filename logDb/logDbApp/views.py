
from django.shortcuts import render
from django.http import HttpResponse
from .models import LogEntry
from rest_framework.views import APIView
from rest_framework.response import Response
from .serializers import LogEntrySerializer

def log(request):
    return render(request, 'logDbApp/log.html', {'log': LogEntry.objects.all()})


class logView(APIView):
    def get(self, request):
        logItems = LogEntry.objects.all()
        serializer = LogEntrySerializer(logItems, many=True)
        return Response({"log entries": serializer.data})

    def post(selfself, request):
        logItem = request.data.get('log entries')

        serializer = LogEntrySerializer(data=logItem)
        print(serializer)
        if serializer.is_valid():
            entry_saved = serializer.save()
        return HttpResponse({"success": "LogEntry '{}' created successfully".format(entry_saved).title})
