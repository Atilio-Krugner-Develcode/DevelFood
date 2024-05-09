package br.com.develfood.develfood.Job;

import br.com.develfood.develfood.Class.RestaurantePromocao;
import br.com.develfood.develfood.Repository.RestaurantePromocaoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class PromotionDateUpdaterJob {

    private final RestaurantePromocaoRepository promocaoRepository;

    public PromotionDateUpdaterJob(RestaurantePromocaoRepository promocaoRepository) {
        this.promocaoRepository = promocaoRepository;
    }

    @Scheduled(fixedRate = 8 * 60 * 60 * 1000)
    public void updatePromotionDates() {
        LocalDate currentTime = LocalDate.now();

        List<RestaurantePromocao> promotions = promocaoRepository.findAll();

        for (RestaurantePromocao promotion : promotions) {
            LocalDate originalEndDate = promotion.getDataFinal();
            LocalDate newEndDate = originalEndDate.plusDays(30);
            promotion.setDataFinal(newEndDate);
        }

        promocaoRepository.saveAll(promotions);
        System.out.println("Datas das promoções atualizadas com sucesso.");
    }
}
