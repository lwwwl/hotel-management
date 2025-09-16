package com.example.hotelmanagement.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelmanagement.dao.entity.MessageTranslate;

@Repository
public interface MessageTranslateRepository extends JpaRepository<MessageTranslate, Long> {

    List<MessageTranslate> findByConversationId(Long conversationId);

    List<MessageTranslate> findByMessageId(Long messageId);

    Optional<MessageTranslate> findByConversationIdAndMessageIdAndLanguage(Long conversationId, Long messageId, String language);
}


