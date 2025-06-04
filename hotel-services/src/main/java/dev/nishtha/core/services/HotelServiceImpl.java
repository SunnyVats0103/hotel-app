package dev.nishtha.core.services;

import dev.nishtha.core.contracts.HotelService;
import dev.nishtha.core.dtos.HotelDTO;
import dev.nishtha.core.dtos.RoomDTO;
import dev.nishtha.core.entities.Hotel;
import dev.nishtha.core.entities.Room;
import dev.nishtha.core.mappers.HotelMapper;
import dev.nishtha.core.mappers.RoomMapper;
import dev.nishtha.core.repositories.HotelRepository;
import dev.nishtha.core.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;

    @Override
    public List<HotelDTO> fetchHotels() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream()
                .map(hotelMapper::mapHotel)
                .toList();
    }

    @Override
    public Optional<HotelDTO> fetchHotelById(Long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        Optional<List<Room>> savedRooms = Optional.empty();
        if (hotel.isPresent()) savedRooms = roomRepository.findByHotelId(hotel.get().getId());

        List<RoomDTO> roomDtos = savedRooms.map(
                rooms -> rooms.stream()
                        .map(roomMapper::toRoomDTO)
                        .toList())
                .orElse(null);

        Optional<HotelDTO> hotelDTO = hotel.map(hotelMapper::mapHotel);
        hotelDTO.ifPresent(dto -> dto.setRooms(roomDtos));

        return hotelDTO;
    }

    @Override
    public HotelDTO saveHotel(HotelDTO hotelDTO) {
        Hotel hotel = hotelMapper.mapHotelDTO(hotelDTO);
        hotel.setIsDeleted(false);

        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.mapHotel(savedHotel);
    }

    @Override
    public HotelDTO updateHotel(HotelDTO hotelDTO) {
        return saveHotel(hotelDTO);
    }

    @Override
    public HotelDTO modifyHotel(Long id, HotelDTO hotelDTO) {
        Optional<Hotel> hotel = hotelRepository.findById(id);

        if (hotel.isPresent()) {
            Hotel hotelEntity = hotel.get();
            if (StringUtils.hasText(hotelDTO.getName()))
                hotelEntity.setName(hotelDTO.getName());
            if (StringUtils.hasText(hotelDTO.getAddress()))
                hotelEntity.setAddress(hotelDTO.getAddress());
            if (StringUtils.hasText(hotelDTO.getCity()))
                hotelEntity.setCity(hotelDTO.getCity());
            if (StringUtils.hasText(hotelDTO.getState()))
                hotelEntity.setState(hotelDTO.getState());
            if (StringUtils.hasText(hotelDTO.getCountry()))
                hotelEntity.setCountry(hotelDTO.getCountry());
            if (StringUtils.hasText(hotelDTO.getPhone()))
                hotelEntity.setPhone(hotelDTO.getPhone());
            if (StringUtils.hasText(hotelDTO.getEmail()))
                hotelEntity.setEmail(hotelDTO.getEmail());
            if (StringUtils.hasText(hotelDTO.getZipCode()))
                hotelEntity.setZipCode(hotelDTO.getZipCode());

            Hotel savedHotel = hotelRepository.save(hotelEntity);
            return hotelMapper.mapHotel(savedHotel);
        } else {
            return saveHotel(hotelDTO);
        }
    }

    @Override
    public void removeHotel(Long id) {
        hotelRepository.deleteById(id);
    }
}
