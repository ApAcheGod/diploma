package com.example.diplom.services.mappers;

import com.example.diplom.entities.Examination;
import com.example.diplom.entities.ExaminationStatus;
import com.example.diplom.entities.dto.ExaminationDto;
import com.example.diplom.services.ExaminationService;
import com.example.diplom.services.SolutionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ExaminationMapper {

    private final ModelMapper modelMapper;
    private final ExaminationService examinationService;
    private final SolutionService solutionService;


    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Examination.class, ExaminationDto.class)
                .addMappings(m -> m.skip(ExaminationDto::setExaminationId))
                .addMappings(m -> m.skip(ExaminationDto::setExaminationStatus))
                .addMappings(m -> m.skip(ExaminationDto::setTeacherId))
                .addMappings(m -> m.skip(ExaminationDto::setTeacherLogin))
                .addMappings(m -> m.skip(ExaminationDto::setTaskId))
                .addMappings(m -> m.skip(ExaminationDto::setTaskName))
                .addMappings(m -> m.skip(ExaminationDto::setGroupId))
                .addMappings(m -> m.skip(ExaminationDto::setGroupName))
                .addMappings(m -> m.skip(ExaminationDto::setSolutionId))
                .addMappings(m -> m.skip(ExaminationDto::setStudentId))
                .addMappings(m -> m.skip(ExaminationDto::setStudentLogin))
                .addMappings(m -> m.skip(ExaminationDto::setSubjectId))
                .addMappings(m -> m.skip(ExaminationDto::setSubjectName))
                .addMappings(m -> m.skip(ExaminationDto::setDateOfDelivery))
                .addMappings(m -> m.skip(ExaminationDto::setDateOfValuation))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(ExaminationDto.class, Examination.class)
                .setPostConverter(toEntityConverter());
    }


    private Converter<ExaminationDto, Examination> toEntityConverter(){
        return mappingContext -> {
            ExaminationDto source = mappingContext.getSource();
            Examination destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Examination, ExaminationDto> toDtoConverter() {
        return mappingContext -> {
            Examination source = mappingContext.getSource();
            ExaminationDto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Examination source, ExaminationDto destination) {

        if (source.getId() != null){
            destination.setExaminationId(source.getId());
        }

        if (source.getExaminationStatus() != null){
            destination.setExaminationStatus(source.getExaminationStatus().getTitle());
        }

        if (source.getDateOfValuation() != null){
            destination.setDateOfValuation(source.getDateOfValuation());
        }

        if (source.getSolution() != null){
            destination.setSolutionId(source.getSolution().getId());
            destination.setStudentId(source.getSolution().getStudent().getId());
            destination.setStudentLogin(source.getSolution().getStudent().getLogin());
            destination.setTaskId(source.getSolution().getTask().getId());
            destination.setTaskName(source.getSolution().getTask().getName());
            destination.setTeacherId(source.getSolution().getTask().getTeacher().getId());
            destination.setTeacherLogin(source.getSolution().getTask().getTeacher().getLogin());
            destination.setGroupId(source.getSolution().getStudent().getGroup().getId());
            destination.setGroupName(source.getSolution().getStudent().getGroup().getName());
            destination.setSubjectId(source.getSolution().getTask().getSubject().getId());
            destination.setSubjectName(source.getSolution().getTask().getSubject().getName());
            destination.setDateOfDelivery(source.getSolution().getDateOfDelivery());
        }
    }

    private void mapSpecificFields(ExaminationDto source, Examination destination) {
        if (source.getSolutionId() != null){
            destination.setSolution(solutionService.findById(source.getSolutionId()));
        }

        if (source.getExaminationStatus() != null){
            destination.setExaminationStatus(ExaminationStatus.getNameByTitle(source.getExaminationStatus()));
        }
    }

    public Examination toEntity(ExaminationDto examinationDto){
        return Objects.isNull(examinationDto) ? null : modelMapper.map(examinationDto, Examination.class);
    }

    public ExaminationDto toDto(Examination examination){
        return Objects.isNull(examination) ? null : modelMapper.map(examination, ExaminationDto.class);
    }
}
