package com.bank.authorization.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.entity.AuditEntity;

@DisplayName("Audit mapper")
public class AuditMapperTest {

    private final AuditMapper auditMapperForTest = new AuditMapperImpl();

    @Test
    @DisplayName("Маппинг в dto")
    void shouldConvertAuditEntityToDto() {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setId(13L);
        auditEntity.setCreatedBy("Jhon Doe");

        AuditDto auditDto = auditMapperForTest.toDto(auditEntity);

        Assertions.assertNotNull(auditDto);
        Assertions.assertEquals(auditEntity.getId(), auditDto.getId());
        Assertions.assertEquals(auditEntity.getCreatedBy(), auditDto.getCreatedBy());
        Assertions.assertNotEquals(auditEntity.getId(), auditDto.getCreatedBy());
    }

    @Test
    @DisplayName("Маппинг в DTO (на вход подан null)")
    void shouldReturnNullIfEntityNotPassedInMethodToDto() {
        AuditDto auditDto = this.auditMapperForTest.toDto(null);

        Assertions.assertNull(auditDto);
    }
}
