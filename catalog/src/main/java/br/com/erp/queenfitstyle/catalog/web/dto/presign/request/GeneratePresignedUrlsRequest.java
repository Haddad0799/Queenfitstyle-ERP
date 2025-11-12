package br.com.erp.queenfitstyle.catalog.web.dto.presign.request;

import java.util.List;

public record GeneratePresignedUrlsRequest(List<ImageUploadRequest> images) {}
