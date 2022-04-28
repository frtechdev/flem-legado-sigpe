package br.org.flem.primeiroemprego.util.validator;

import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.util.CoreUtil;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 * Descreva Sua Classe ValidacaoEgressoImpl
 *
 * @author <code>tscortes@flem.org.br</code> 05/04/2019
 * @version 1.0
 */
public class ValidacaoEgressoImpl implements ValidacaoEgresso {

    private static final String MSG_ERRO = "Todas as Perguntas devem ser respondidas!";

    @Override
    public void validarCursoSuperior(Egresso egresso) throws BusinessException {
        if (CoreUtil.isIgual(egresso.getCursoSuperiorConcluido(), "NAO")) {
            if (StringUtils.isEmpty(egresso.getCursoSuperior())) {
                throw new BusinessException(MSG_ERRO);
            } else {
                if (CoreUtil.isIgual(egresso.getCursoSuperior(), "NAO")) {
                    if (StringUtils.isEmpty(egresso.getCursoTecnicoProfissionalizante()) || StringUtils.isEmpty(egresso.getPretendeFazerCursoSuperior())) {
                        throw new BusinessException(MSG_ERRO);
                    }
                }
            }
        }
    }

    @Override
    public void validarCursoTecnico(Egresso egresso) throws BusinessException {
        if (StringUtils.isNotEmpty(egresso.getCursoTecnicoProfissionalizante()) && egresso.getCursoTecnicoProfissionalizante().equals("SIM")) {
            egresso.setCursoSuperior("NAO");
            egresso.setCursoSuperiorConcluido("NAO");
            if (StringUtils.isNotEmpty(egresso.getCursoTecnicoProfissionalizante()) && egresso.getCursoTecnicoProfissionalizante().equals("SIM")
                    && (egresso.getTipoInstituicaoTecnica() == null
                    || StringUtils.isEmpty(egresso.getNomeCursoTecnico())
                    || egresso.getRendaPPEAjuda() == null)) {
                throw new BusinessException(MSG_ERRO);
            }
        } else {
            egresso.setTipoInstituicaoTecnica(null);
            egresso.setNomeCursoTecnico(null);
        }

    }

    @Override
    public void validarCursoSuperiorConcluido(Egresso egresso) throws BusinessException {
        if (StringUtils.isNotEmpty(egresso.getCursoSuperiorConcluido()) && egresso.getCursoSuperiorConcluido().equals("SIM")) {
            egresso.setCursoTecnicoProfissionalizante(null);
            egresso.setCursoSuperior(null);
            if ((egresso.getTipoInstituicaoSuperior() == null
                    || StringUtils.isEmpty(egresso.getNomeInstituicaoSuperior())
                    || StringUtils.isEmpty(egresso.getNomeCursoGraduacao())
                    || egresso.getAnoMatricula() == null
                    || egresso.getAnoConclusaoCurso() == null
                    || egresso.getRendaPPEAjuda() == null)) {
                throw new BusinessException(MSG_ERRO);
            }
        } else {
            egresso.setAnoConclusaoCurso(null);
        }

    }

    /**
     *
     * @param egresso
     */
    public void limparGraduacao(Egresso egresso) {

        egresso.setNomeInstituicaoSuperior(null);
        egresso.setNomeCursoGraduacao(null);
        egresso.setAnoMatricula(null);
        egresso.setModalidadeGraduacao(null);
        egresso.setTipoInstituicaoSuperior(null);
        egresso.setDataRespostaSemestre(null);
        egresso.setSemestre(null);
        egresso.setAnoConclusaoCurso(null);

    }

    /**
     * @param egresso
     */
    public void limparCursoTecnico(Egresso egresso) {
        egresso.setNomeCursoTecnico(null);
        egresso.setTipoInstituicaoTecnica(null);
        egresso.setNomeInstituicaoTecnico(null);
    }

    @Override
    public void validarCursoSuperiorAndamento(Egresso egresso) throws BusinessException {
        if (StringUtils.isNotEmpty(egresso.getCursoSuperior()) && egresso.getCursoSuperior().equals("SIM")) {
            egresso.setCursoTecnicoProfissionalizante(null);
            egresso.setCursoSuperiorConcluido("NAO");
            if (egresso.getTipoInstituicaoSuperior() == null
                    || StringUtils.isEmpty(egresso.getNomeInstituicaoSuperior())
                    || StringUtils.isEmpty(egresso.getNomeCursoGraduacao())
                    || egresso.getAnoMatricula() == null
                    || egresso.getSemestre() == null || egresso.getDataRespostaSemestre() == null
                    || egresso.getRendaPPEAjuda() == null) {
                throw new BusinessException(MSG_ERRO);
            }
        } else {
            egresso.setSemestre(null);
            egresso.setDataRespostaSemestre(null);
        }

    }

    /**
     *
     * @param egresso
     */
    public void validarRespostasNegativa(Egresso egresso) {
        if (!CoreUtil.isIgual(egresso.getCursoSuperiorConcluido(), "SIM") && !CoreUtil.isIgual(egresso.getCursoSuperior(), "SIM")) {
            limparGraduacao(egresso);
        }
        if (!CoreUtil.isIgual(egresso.getCursoTecnicoProfissionalizante(), "SIM")) {
            limparCursoTecnico(egresso);
        }
    }

    public void validarRespostasCursoSuperior(Egresso egresso) throws BusinessException {
        if (StringUtils.isEmpty(egresso.getCursoSuperiorConcluido()) || egresso.getCursoSuperiorConcluido() == null) {
            limparGraduacao(egresso);
            limparCursoTecnico(egresso);
            egresso.setCursoSuperiorConcluido(null);
            egresso.setRendaPPEAjuda(null);
            egresso.setPretendeFazerCursoSuperior(null);
        } else {
            validarCursoSuperiorConcluido(egresso);
            validarCursoSuperior(egresso);
            validarRespostasNegativa(egresso);
            validarCursoSuperiorAndamento(egresso);
            validarCursoTecnico(egresso);
        }

    }

}
