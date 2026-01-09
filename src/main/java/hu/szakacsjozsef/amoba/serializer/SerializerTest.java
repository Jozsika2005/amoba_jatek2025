package hu.szakacsjozsef.amoba.serializer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Test;

class SerializerTest {

    @Test
    void saveAndLoad_works() throws Exception {
        char[][] board = {
                {'X', '.', '.'},
                {'.', 'O', '.'},
                {'.', '.', '.'}
        };

        File file = new File("test-save.json");

        Serializer.save(board, file);
        char[][] loaded = Serializer.load(file);

        assertNotNull(loaded);

        file.delete();
    }
}
