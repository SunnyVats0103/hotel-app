package dev.nishtha.core.services;

import dev.nishtha.core.contracts.RoomService;
import dev.nishtha.core.dtos.RoomDTO;
import dev.nishtha.core.entities.Hotel;
import dev.nishtha.core.entities.Room;
import dev.nishtha.core.mappers.RoomMapper;
import dev.nishtha.core.repositories.HotelRepository;
import dev.nishtha.core.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    @Override
    public List<RoomDTO> fetchRooms() {
        List<Room> savedRooms = roomRepository.findAll();
        return savedRooms.stream().map(roomMapper::toRoomDTO).toList();
    }

    @Override
    public RoomDTO fetchRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);

        return room.map(roomMapper::toRoomDTO).orElse(null);
    }

    @Override
    public RoomDTO saveRoom(RoomDTO roomDTO) {
        Room room = roomMapper.toRoom(roomDTO);
        room.setIsBooked(false);
        Optional<Hotel> savedHotel = hotelRepository.findById(Long.parseLong(roomDTO.getHotelId()));
        if (savedHotel.isPresent()) {
            room.setHotel(savedHotel.get());
        } else {
            throw new RuntimeException("Hotel not found");
        }

        Room savedRoom = roomRepository.save(room);

        return roomMapper.toRoomDTO(savedRoom);
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
            return roomMapper.toRoomDTO(updatedRoom);
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
            return roomMapper.toRoomDTO(updatedRoom);
        }

        return saveRoom(roomDTO);
    }

    @Override
    public void removeRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
