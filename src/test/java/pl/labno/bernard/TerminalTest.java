package pl.labno.bernard;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
public class TerminalTest {

    @Rule
    public ExpectedException expectedException= ExpectedException.none();

    @Test
    public void pierwsza_Null_theowIllegalArgumentException() {
        Connection connection= Mockito.mock(Connection.class);
        Terminal termn=new Terminal(connection);

        expectedException.expect(IllegalArgumentException.class);
        termn.sendLine(null);
    }

    @Test
    public void druga_noConnection_throwIllegalArgumentException() {
        Connection connection= Mockito.mock(Connection.class);
        when(connection.isConnected()).thenReturn(false);

        Terminal ter=new Terminal(connection);
        expectedException.expect(IllegalStateException.class);
        ter.sendLine("Test");
    }

    @Test
    public void trzecia_connectionSendLineThrowException_throwllegalArgumentException() {
        Connection connection= Mockito.mock(Connection.class);
        when(connection.isConnected()).thenReturn(true);

        when(connection.sendLine("Test")).thenThrow(UnknownCommandException.class);
        Terminal ter=new Terminal(connection);

        expectedException.expect(IllegalStateException.class);
        ter.sendLine("Test");
    }

    @Test
    public void czwarta_noConnection_ErrorMassage() {

        Connection connection= Mockito.mock(Connection.class);
        when(connection.isConnected()).thenReturn(false);
        Terminal ter=new Terminal(connection);

        try {
            ter.sendLine("Test");
        }
        catch(IllegalStateException e){}
        Assert.assertEquals("Terminal is not connected", ter.getErrorMessage());
    }
}