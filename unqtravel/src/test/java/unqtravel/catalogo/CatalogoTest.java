package unqtravel.catalogo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

class CatalogoTest {

    @Test
    void testVerificarMockito() {
        // 1. Crear un Mock de una lista de Java
        @SuppressWarnings("unchecked")
        List<String> listaMock = mock(List.class);

        // 2. Definir el comportamiento del Mock (Stubbing)
        when(listaMock.get(0)).thenReturn("Funciona Mockito");

        // 3. Ejecutar y verificar la aserción con JUnit 5
        assertEquals("Funciona Mockito", listaMock.get(0));

        // 4. Verificar que se llamó al método get(0)
        verify(listaMock).get(0);
    }
}