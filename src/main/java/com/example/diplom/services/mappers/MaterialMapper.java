package com.example.diplom.services.mappers;

import com.example.diplom.entities.Material;
import com.example.diplom.entities.dto.MaterialDto;
import com.example.diplom.services.SubjectService;
import com.example.diplom.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MaterialMapper {

    private final ModelMapper modelMapper;

    private final SubjectService subjectService;
    private final TeacherService teacherService;

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Material.class, MaterialDto.class)
                .addMappings(m -> m.skip(MaterialDto::setSubjectId))
                .addMappings(m -> m.skip(MaterialDto::setTeacherId))
                .addMappings(m -> m.skip(MaterialDto::setTeacherName))
                .addMappings(m -> m.skip(MaterialDto::setSubjectName))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(MaterialDto.class, Material.class)
                .addMappings(m -> m.skip(Material::setSubject))
                .addMappings(m -> m.skip(Material::setTeacher))
                .setPostConverter(toEntityConverter());
    }

    private Converter<MaterialDto, Material> toEntityConverter(){
        return mappingContext -> {
            MaterialDto source = mappingContext.getSource();
            Material destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private Converter<Material, MaterialDto> toDtoConverter() {
        return mappingContext -> {
            Material source = mappingContext.getSource();
            MaterialDto destination = mappingContext.getDestination();
            mapSpecificFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    private void mapSpecificFields(Material source, MaterialDto destination) {

        if (source.getSubject() != null){
            destination.setSubjectId(source.getSubject().getId());
            destination.setSubjectName(source.getSubject().getName());
        }

        if (source.getTeacher() != null){
            destination.setTeacherId(source.getTeacher().getId());
            destination.setTeacherName(source.getTeacher().getTeacherName());
        }
    }

    private void mapSpecificFields(MaterialDto source, Material destination) {

        if (source.getSubjectId() != null){
            destination.addSubject(subjectService.findById(source.getSubjectId()));
        }

        if (source.getTeacherId() != null){
            destination.addTeacher(teacherService.findById(source.getTeacherId()));
        }
    }

    public Material toEntity(MaterialDto materialDto){
        return Objects.isNull(materialDto) ? null : modelMapper.map(materialDto, Material.class);
    }

    public MaterialDto toDto(Material material){
        return Objects.isNull(material) ? null : modelMapper.map(material, MaterialDto.class);
    }
}
