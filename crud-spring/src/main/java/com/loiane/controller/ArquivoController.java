import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ArquivoController {

    @Value("${file.upload-dir}")
    private String uploadPath;

    @PostMapping("/api/courses/{id}/upload")
    public ResponseEntity<?> uploadArquivo(@PathVariable Long id, @RequestPart("file") MultipartFile arquivo) {
        try {
            // Verifica se o arquivo é válido e não está vazio
            if (arquivo != null && !arquivo.isEmpty()) {
                // Cria o diretório de destino se ele não existir
                Path uploadDirPath = Paths.get(uploadPath);
                if (!Files.exists(uploadDirPath)) {
                    Files.createDirectories(uploadDirPath);
                }
                // Cria um caminho para o arquivo no diretório de destino
                Path path = Paths.get(uploadPath + File.separator + arquivo.getOriginalFilename());
                // Salva o arquivo no diretório de destino
                Files.write(path, arquivo.getBytes());
                // Retorna uma resposta de sucesso
                return ResponseEntity.ok().build();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // Retorna uma resposta de erro em caso de falha
        return ResponseEntity.badRequest().build();
    }
}