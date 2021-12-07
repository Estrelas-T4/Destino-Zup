package br.com.zup.DestinoZup.destino;

import br.com.zup.DestinoZup.categoria.CategoriaRepository;
import br.com.zup.DestinoZup.destino.exceptions.RegiaoNaoEncontradaException;
import br.com.zup.DestinoZup.regiao.Regiao;
import br.com.zup.DestinoZup.regiao.RegiaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DestinoServiceTest {

    @MockBean
    private DestinoRepository destinoRepository;
    @MockBean
    private RegiaoRepository regiaoRepository;
    @MockBean
    private CategoriaRepository categoriaRepository;

    @Autowired
    private DestinoService destinoService;

    private Destino destino;

    @BeforeEach
    private void setup(){
        destino = new Destino();
        Regiao regiao = new Regiao();
        regiao.setNome("Xablau");
        destino.setRegiao(regiao);

    }

    @Test
    public void testarSalvarDestinoCaminhoRuim(){
        Mockito.when(regiaoRepository.existsById(Mockito.anyString())).thenReturn(false);

        Assertions.assertThrows(RegiaoNaoEncontradaException.class, () -> {destinoService.salvarDestino(destino);});
    }

    @Test
    public void testarSalvarDestinoCaminhoBom(){
        Mockito.when(regiaoRepository.existsById(Mockito.anyString())).thenReturn(true);
        Mockito.when(destinoRepository.save(Mockito.any(Destino.class))).thenReturn(destino);

        destinoService.salvarDestino(destino);
        //Mockito verify verifica quantas vezes um metodo é chamado dentro do metodo testado (salvarDestino nesse caso)
        //Nesse mockito estamos verificando o metodo save do destinoRepository
        Mockito.verify(destinoRepository, Mockito.times(1)).save(destino);
    }
}
