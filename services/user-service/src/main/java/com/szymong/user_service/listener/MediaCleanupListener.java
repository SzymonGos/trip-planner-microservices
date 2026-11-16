package com.szymong.user_service.listener;

import com.szymong.user_service.client.MediaClient;
import com.szymong.user_service.event.MediaImageDeletionRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class MediaCleanupListener {
  private final MediaClient mediaClient;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handle(MediaImageDeletionRequestedEvent event) {
    log.info("Media cleanup triggered. Public IDs: {}", event.publicIds());

    for (String publicId : event.publicIds()) {
      try {
        log.info("Attempting to delete image: {}", publicId);
        mediaClient.deleteImage(publicId);
        log.info("Image deletion completed for: {}", publicId);
      } catch (RuntimeException exception) {
        log.error("Failed to delete image with publicId: {}", publicId, exception);
      }
    }
  }
}