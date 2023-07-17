package com.techforb.challenge.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentTypeTest {

    @Test
    void testEnumValues() {
        DocumentType[] documentTypes = DocumentType.values();

        assertEquals(3, documentTypes.length);
        assertTrue(containsDocumentType(documentTypes, DocumentType.DNI));
        assertTrue(containsDocumentType(documentTypes, DocumentType.CEDULA));
        assertTrue(containsDocumentType(documentTypes, DocumentType.PASAPORTE));
    }

    private boolean containsDocumentType(DocumentType[] documentTypes, DocumentType documentType) {
        for (DocumentType type : documentTypes) {
            if (type.equals(documentType)) {
                return true;
            }
        }
        return false;
    }
}
