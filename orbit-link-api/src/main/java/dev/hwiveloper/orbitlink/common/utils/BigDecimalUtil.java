package dev.hwiveloper.orbitlink.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class BigDecimalUtil {
    /**
     * 소수점 2자리까지 고려해 분할.
     * 총합 = totalAmount 정확히 맞춤.
     *
     * @param totalAmount 전체 금액
     * @param months      할부 개월 수
     * @return 회차별 금액 리스트
     */
    public static List<BigDecimal> splitAmountByInstallment(BigDecimal totalAmount, int months, Integer decimalPlaces) {
        if (months <= 0) {
            throw new IllegalArgumentException("할부 개월 수는 1 이상이어야 합니다.");
        }

        if (decimalPlaces == null || decimalPlaces < 0 || decimalPlaces > 2) {
            throw new IllegalArgumentException("decimalPlaces는 0~2 사이의 정수여야 합니다.");
        }

        // 기본 금액 (소수점 2자리까지)
        BigDecimal monthlyAmount = totalAmount
                .divide(BigDecimal.valueOf(months), decimalPlaces, RoundingMode.DOWN);

        List<BigDecimal> result = new ArrayList<>();
        BigDecimal sum = BigDecimal.ZERO;

        // 1. 각 회차 금액 분할
        for (int i = 0; i < months; i++) {
            result.add(monthlyAmount);
            sum = sum.add(monthlyAmount);
        }

        // 2. 보정 단위 계산: decimalPlaces에 따라 1, 0.1, 0.01 등
        BigDecimal unit = BigDecimal.ONE.scaleByPowerOfTen(-decimalPlaces);

        // 3. 오차 계산 및 보정
        BigDecimal diff = totalAmount.subtract(sum).setScale(decimalPlaces, RoundingMode.HALF_UP);

        // 보정 반복
        int i = 0;
        while (diff.compareTo(BigDecimal.ZERO) > 0 && i < months) {
            result.set(i, result.get(i).add(unit));
            diff = diff.subtract(unit);
            i++;
        }

        return result;
    }

}
