package ticket.booking.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UserServiceUtil
 * Tests password hashing and verification functionality
 */
public class UserServiceUtilTest {

    @Test
    @DisplayName("Test password hashing generates non-null hash")
    public void testHashPassword_ReturnsNonNullHash() {
        // Arrange: Set up test data
        String plainPassword = "mySecurePassword123";
        
        // Act: Perform the action we're testing
        String hashedPassword = UserServiceUtil.hashPassword(plainPassword);
        
        // Assert: Verify the result
        assertNotNull(hashedPassword, "Hashed password should not be null");
        assertFalse(hashedPassword.isEmpty(), "Hashed password should not be empty");
    }

    @Test
    @DisplayName("Test password hash is different from plain password")
    public void testHashPassword_IsDifferentFromPlainPassword() {
        // Arrange
        String plainPassword = "password123";
        
        // Act
        String hashedPassword = UserServiceUtil.hashPassword(plainPassword);
        
        // Assert
        assertNotEquals(plainPassword, hashedPassword, 
            "Hashed password should be different from plain password");
    }

    @Test
    @DisplayName("Test same password generates different hashes (salting)")
    public void testHashPassword_GeneratesDifferentHashesForSamePassword() {
        // Arrange
        String plainPassword = "testPassword";
        
        // Act: Hash the same password twice
        String hash1 = UserServiceUtil.hashPassword(plainPassword);
        String hash2 = UserServiceUtil.hashPassword(plainPassword);
        
        // Assert: Different hashes due to different salts
        assertNotEquals(hash1, hash2, 
            "Same password should generate different hashes due to salting");
    }

    @Test
    @DisplayName("Test correct password verification returns true")
    public void testCheckPassword_WithCorrectPassword_ReturnsTrue() {
        // Arrange
        String plainPassword = "correctPassword";
        String hashedPassword = UserServiceUtil.hashPassword(plainPassword);
        
        // Act
        boolean result = UserServiceUtil.checkPassword(plainPassword, hashedPassword);
        
        // Assert
        assertTrue(result, "Correct password should verify successfully");
    }

    @Test
    @DisplayName("Test incorrect password verification returns false")
    public void testCheckPassword_WithIncorrectPassword_ReturnsFalse() {
        // Arrange
        String correctPassword = "correctPassword";
        String incorrectPassword = "wrongPassword";
        String hashedPassword = UserServiceUtil.hashPassword(correctPassword);
        
        // Act
        boolean result = UserServiceUtil.checkPassword(incorrectPassword, hashedPassword);
        
        // Assert
        assertFalse(result, "Incorrect password should fail verification");
    }

    @Test
    @DisplayName("Test password verification is case-sensitive")
    public void testCheckPassword_IsCaseSensitive() {
        // Arrange
        String password = "MyPassword";
        String hashedPassword = UserServiceUtil.hashPassword(password);
        String differentCasePassword = "mypassword";
        
        // Act
        boolean result = UserServiceUtil.checkPassword(differentCasePassword, hashedPassword);
        
        // Assert
        assertFalse(result, "Password verification should be case-sensitive");
    }

    @Test
    @DisplayName("Test empty password can be hashed")
    public void testHashPassword_WithEmptyPassword() {
        // Arrange
        String emptyPassword = "";
        
        // Act
        String hashedPassword = UserServiceUtil.hashPassword(emptyPassword);
        
        // Assert
        assertNotNull(hashedPassword, "Empty password should still generate a hash");
        assertFalse(hashedPassword.isEmpty(), "Hash of empty password should not be empty");
    }

    @Test
    @DisplayName("Test special characters in password")
    public void testHashPassword_WithSpecialCharacters() {
        // Arrange
        String passwordWithSpecialChars = "p@$$w0rd!#123";
        
        // Act
        String hashedPassword = UserServiceUtil.hashPassword(passwordWithSpecialChars);
        boolean isValid = UserServiceUtil.checkPassword(passwordWithSpecialChars, hashedPassword);
        
        // Assert
        assertNotNull(hashedPassword, "Password with special characters should be hashed");
        assertTrue(isValid, "Password with special characters should verify correctly");
    }

    @Test
    @DisplayName("Test very long password")
    public void testHashPassword_WithLongPassword() {
        // Arrange
        String longPassword = "a".repeat(100); // 100 character password
        
        // Act
        String hashedPassword = UserServiceUtil.hashPassword(longPassword);
        boolean isValid = UserServiceUtil.checkPassword(longPassword, hashedPassword);
        
        // Assert
        assertNotNull(hashedPassword, "Long password should be hashed successfully");
        assertTrue(isValid, "Long password should verify correctly");
    }
}

