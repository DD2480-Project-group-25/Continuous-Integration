
from django.shortcuts import render
from .models import LogEntry
from rest_framework import viewsets

def log(request):
    return render(request, 'logDbApp/log.html', {'log': LogEntry.objects.all()})

class logEntryViews(viewsets.ModelViewSet):
    queryset = logEntry.objects.all()