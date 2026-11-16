package com.szymong.user_service.event;

import java.util.List;

public record MediaImageDeletionRequestedEvent(List<String> publicIds) {
  public MediaImageDeletionRequestedEvent {
    publicIds = List.copyOf(publicIds);
  }
}