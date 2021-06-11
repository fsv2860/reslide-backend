package com.mygroup.backendReslide.service;

import com.mygroup.backendReslide.dto.ContactTypeDto;
import com.mygroup.backendReslide.exceptions.alreadyExists.ContactTypeExistsException;
import com.mygroup.backendReslide.exceptions.notFound.ContactNotFoundException;
import com.mygroup.backendReslide.mapper.ContactTypeMapper;
import com.mygroup.backendReslide.model.ContactType;
import com.mygroup.backendReslide.repository.ContactTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContactTypeService {
    private final ContactTypeMapper contactTypeMapper;
    private final ContactTypeRepository contactTypeRepository;

    @Transactional
    public void create(ContactTypeDto contactTypeRequest) {
        if(contactTypeRepository.findByTypeIgnoreCase(contactTypeRequest.getType()).isPresent()){
            throw new ContactTypeExistsException(contactTypeRequest.getType());
        }

        contactTypeRepository.save(contactTypeMapper.mapToEntity(contactTypeRequest));
    }

    @Transactional
    public void update(ContactTypeDto contactTypeRequest){
        ContactType contactType = contactTypeRepository.findById(contactTypeRequest.getId())
                                    .orElseThrow(()-> new ContactNotFoundException(contactTypeRequest.getId()));

        if(contactTypeRepository.findByTypeIgnoreCase(contactTypeRequest.getType()).isPresent()){
            throw new ContactTypeExistsException(contactTypeRequest.getType());
        }

        contactType.setType(contactTypeRequest.getType());
        contactType.setNotes(contactTypeRequest.getNotes());
        contactType.setEnabled(contactTypeRequest.isEnabled());
        contactTypeRepository.save(contactTypeMapper.mapToEntity(contactTypeRequest));
    }

    @Transactional(readOnly = true)
    public List<ContactTypeDto> getAll() {
        return contactTypeRepository.findByStatus(true)
                .stream()
                .map(contactTypeMapper :: mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ContactTypeDto> search(String type) {
        return contactTypeRepository.findByTypeContainsIgnoreCaseAndStatus(type, true)
                .stream()
                .map(contactTypeMapper :: mapToDto) // .map((contactType) -> contactTypeMapper.mapToDto(contactType))
                .collect(Collectors.toList());
    }
}
