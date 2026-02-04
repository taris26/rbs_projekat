package com.zuehlke.securesoftwaredevelopment.controller;

import com.zuehlke.securesoftwaredevelopment.config.AuditLogger;
import com.zuehlke.securesoftwaredevelopment.domain.Destination;
import com.zuehlke.securesoftwaredevelopment.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DestinationController {

    private static final Logger LOG = LoggerFactory.getLogger(BooksController.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(BooksController.class);

    private DestinationRepository destinationRepository;
    private CommentRepository commentRepository;
    private RatingRepository ratingRepository;
    private PersonRepository userRepository;
    private VoucherRepository voucherRepository;
    private TagRepository tagRepository;
}
