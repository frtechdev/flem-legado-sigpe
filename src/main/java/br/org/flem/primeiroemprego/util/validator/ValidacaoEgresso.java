package br.org.flem.primeiroemprego.util.validator;

import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.exception.BusinessException;

/**
 *
 * @brief Classe ValidacaoEgresso
 * @author tscortes
 * @date   05/04/2019
 * @version 1.0
 */
public interface ValidacaoEgresso {
    
    void validarCursoSuperior(Egresso egresso) throws BusinessException;
    void validarCursoTecnico(Egresso egresso) throws BusinessException;
    void validarCursoSuperiorConcluido(Egresso egresso) throws BusinessException;
    void validarCursoSuperiorAndamento(Egresso egresso) throws BusinessException;

}
