package in.sujal.expensio.service;


import in.sujal.expensio.dto.ExpenseDTO;
import in.sujal.expensio.dto.IncomeDTO;
import in.sujal.expensio.entity.CategoryEntity;
import in.sujal.expensio.entity.ExpenseEntity;
import in.sujal.expensio.entity.IncomeEntity;
import in.sujal.expensio.entity.ProfileEntity;
import in.sujal.expensio.repository.CategoryRepository;
import in.sujal.expensio.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final CategoryRepository categoryRepository;
    private final IncomeRepository incomeRepository;
    private final ProfileService profileService;

    public IncomeDTO addIncome(IncomeDTO dto){
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        IncomeEntity newIncome = toEntity(dto,profile,category);
        newIncome = incomeRepository.save(newIncome);
        return toDTO(newIncome);
    }

    //Retrieve all incomes for current month/based on start date and end date
    public List<IncomeDTO> getCurrentMonthIncomesForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<IncomeEntity> list = incomeRepository.findByProfileIdAndDateBetween(profile.getId(),startDate,endDate);
        return list.stream().map(this::toDTO).toList();
    }

    //helper methods
    private IncomeEntity toEntity(IncomeDTO dto, ProfileEntity profile, CategoryEntity category){
        return IncomeEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .profile(profile)
                .category(category)
                .build();
    }

    private IncomeDTO toDTO(IncomeEntity entity){
        return IncomeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .categoryId(entity.getCategory() != null? entity.getCategory().getId() : null )
                .categoryName(entity.getCategory() != null? entity.getCategory().getName() : "N/A" )
                .amount(entity.getAmount())
                .date(entity.getDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();


    }
}
