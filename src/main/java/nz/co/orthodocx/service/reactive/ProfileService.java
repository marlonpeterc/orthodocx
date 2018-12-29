package nz.co.orthodocx.service.reactive;

import nz.co.orthodocx.repository.mongodb.reactive.ReactiveProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ReactiveProfileRepository profileRepository;


}
