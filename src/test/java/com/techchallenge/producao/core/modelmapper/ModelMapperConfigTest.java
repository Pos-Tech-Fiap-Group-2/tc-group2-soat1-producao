package com.techchallenge.producao.core.modelmapper;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ModelMapperConfigTest {

	@InjectMocks
	private ModelMapperConfig config;
	
    @BeforeEach
    private void setup() {
    	MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void config() {
    	assertNotNull(config.modelMapper());
    }
}
