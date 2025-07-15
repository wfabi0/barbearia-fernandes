package com.onebit.barbearia_fernandes.repository.specification;

import com.onebit.barbearia_fernandes.dto.AgendamentoFilter;
import com.onebit.barbearia_fernandes.model.Agendamento;
import com.onebit.barbearia_fernandes.model.StatusAgendamento;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoSpecification {

    public static Specification<Agendamento> build(AgendamentoFilter filter) {
        List<Specification<Agendamento>> specs = new ArrayList<>();

        if (filter.barbeiroId() != null) {
            specs.add(comBarbeiroId(filter.barbeiroId()));
        }
        if (filter.clienteId() != null) {
            specs.add(comClienteId(filter.clienteId()));
        }
        if (filter.status() != null) {
            specs.add(comStatus(filter.status()));
        }
        if (filter.data() != null) {
            specs.add(porData(filter.data()));
        }

        if (specs.isEmpty()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

        return specs.stream().reduce(Specification::and).get();
    }

    // MÉTODO CORRIGIDO
    private static Specification<Agendamento> comBarbeiroId(Long barbeiroId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("barbeiro").get("userId"), barbeiroId);
    }

    // MÉTODO CORRIGIDO
    private static Specification<Agendamento> comClienteId(Long clienteId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("cliente").get("userId"), clienteId);
    }

    private static Specification<Agendamento> comStatus(StatusAgendamento statusAgendamento) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), statusAgendamento);
    }

    private static Specification<Agendamento> porData(LocalDate data) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(
                        root.get("dataHora"),
                        data.atStartOfDay(),
                        data.atTime(LocalTime.MAX)
                );
    }

}