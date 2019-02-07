
from rest_framework import serializers
from logDbApp.models import LogEntry


class LogEntrySerializer(serializers.Serializer):
    commit_id = serializers.CharField(max_length=100)
    start = serializers.CharField(max_length=100)
    status = serializers.CharField(max_length=100)

    def create(self, validated_data):
        return LogEntry.objects.create(**validated_data)