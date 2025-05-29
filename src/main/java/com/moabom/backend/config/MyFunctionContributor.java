package com.moabom.backend.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.BasicType;
import org.hibernate.type.StandardBasicTypes;

public class MyFunctionContributor implements FunctionContributor {
    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        // MATCH … AGAINST는 DOUBLE로 반환하므로 DOUBLE 타입으로 등록합니다.
        BasicType<Double> resultType = functionContributions
            .getTypeConfiguration()
            .getBasicTypeRegistry()
            .resolve(StandardBasicTypes.DOUBLE);

        functionContributions.getFunctionRegistry().registerPattern(
            // 함수 이름: JPA에서 cb.function("match_against", …)로 호출할 이름
            "match_against",
            // SQL 템플릿: MATCH(col) AGAINST(? IN BOOLEAN MODE)
            "match (?1) against (?2 in boolean mode)",
            resultType
        );
    }
}