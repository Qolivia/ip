package saru;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parseIndexOrThrow_validNumber_returnsParsedInt() throws SaruException {
        assertEquals(3, Parser.parseIndexOrThrow("3"));
        assertEquals(7, Parser.parseIndexOrThrow("   7  "));
    }

    @Test
    public void parseIndexOrThrow_invalid_throwsSaruException() {
        SaruException e = assertThrows(SaruException.class, () -> Parser.parseIndexOrThrow("abc"));
        assertTrue(e.getMessage().contains("valid task number"));
    }

    @Test
    public void parseCreateCommand_todoWithDescription_returnsTodo() throws SaruException {
        Task t = Parser.parseCreateCommand("todo borrow book");
        assertTrue(t instanceof Todo);
        assertTrue(t.toString().contains("borrow book"));
    }

    @Test
    public void parseCreateCommand_deadlineInvalidDate_throwsSaruException() {
        SaruException e = assertThrows(SaruException.class,
                () -> Parser.parseCreateCommand("deadline return book /by not-a-date"));
        assertTrue(e.getMessage().contains("Invalid date"));
    }
}
