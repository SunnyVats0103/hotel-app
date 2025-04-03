package dev.nishtha.services;

import dev.nishtha.contracts.RoomService;
import dev.nishtha.dtos.RoomDTO;
import dev.nishtha.entities.Hotel;
import dev.nishtha.entities.Room;
import dev.nishtha.mappers.RoomMapper;
import dev.nishtha.repositories.HotelRepository;
import dev.nishtha.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Override
    public List<RoomDTO> fetchRooms() {
        List<Room> savedRooms = roomRepository.findAll();
        return savedRooms.stream().map(RoomMapper::toRoomDTO).toList();
    }

    @Override
    public RoomDTO fetchRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);

        return room.map(RoomMapper::toRoomDTO).orElse(null);
    }

    @Override
    public RoomDTO saveRoom(RoomDTO roomDTO) {
        Room room = RoomMapper.toRoom(roomDTO);
        room.setIsBooked(false);
        Optional<Hotel> savedHotel = hotelRepository.findById(Long.parseLong(roomDTO.getHotelId()));
        if (savedHotel.isPresent()) {
            room.setHotel(savedHotel.get());
        } else {
            throw new RuntimeException("Hotel not found");
        }

        Room savedRoom = roomRepository.save(room);

        return RoomMapper.toRoomDTO(savedRoom);
    }

    @Override
    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        Room savedRoom = roomRepository.findById(id).orElse(null);

        if (savedRoom != null) {
            savedRoom.setRoomNumber(roomDTO.getRoomNumber());
            savedRoom.setRoomType(roomDTO.getRoomType());
            savedRoom.setCapacity(roomDTO.getCapacity());
            savedRoom.setDescription(roomDTO.getDescription());
            savedRoom.setPrice(roomDTO.getPrice());
            savedRoom.setIsBooked(roomDTO.getIsBooked());

            Room updatedRoom = roomRepository.save(savedRoom);
            return RoomMapper.toRoomDTO(updatedRoom);
        }

        return saveRoom(roomDTO);
    }

    @Override
    public RoomDTO modifyRoom(Long id, RoomDTO roomDTO) {
        Room savedRoom = roomRepository.findById(id).orElse(null);

        if (savedRoom != null) {
            if (roomDTO.getRoomNumber() != null)
                savedRoom.setRoomNumber(roomDTO.getRoomNumber());
            if (roomDTO.getRoomType() != null)
                savedRoom.setRoomType(roomDTO.getRoomType());
            if (roomDTO.getCapacity() != null)
                savedRoom.setCapacity(roomDTO.getCapacity());
            if (roomDTO.getDescription() != null)
                savedRoom.setDescription(roomDTO.getDescription());
            if (roomDTO.getPrice() != null)
                savedRoom.setPrice(roomDTO.getPrice());
            if (roomDTO.getIsBooked() != null)
                savedRoom.setIsBooked(roomDTO.getIsBooked());
            Room updatedRoom = roomRepository.save(savedRoom);
            return RoomMapper.toRoomDTO(updatedRoom);
        }

        return saveRoom(roomDTO);
    }

    @Override
    public void removeRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
