
package acme.features.technician.involvedIn;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.maintanenceRecords.InvolvedIn;
import acme.entities.maintanenceRecords.MaintanenceRecord;
import acme.entities.maintanenceRecords.Task;

@Repository
public interface TechnicianInvolvedInRepository extends AbstractRepository {

	//encontrar todas las task de 
	//cambiado

	//encontrar un record en concreto
	@Query("SELECT f FROM MaintanenceRecord f WHERE f.id=:id")
	MaintanenceRecord findRecordById(int id);

	@Query("select b from Task b where b.id=:id")
	Task findTaskById(int id);

	@Query("select b from InvolvedIn b where b.maintanenceRecord.technician.id=:technicianId")
	Collection<InvolvedIn> findAllInvolvedInByTechnicianId(int technicianId);

	//cambiado
	@Query("select b.maintanenceRecord from InvolvedIn b where b.id=?1")
	MaintanenceRecord findOneRecordByInvolvedIn(int id);

	//cambiado
	@Query("select b.task from InvolvedIn b where b.id=?1")
	Task findOneTaskByInvolvedIn(int id);

	//cambiado
	@Query("select b from InvolvedIn b where b.id=:id")
	InvolvedIn findInvolvedIn(int id);

	//encontrar las tasks de un tecnico
	@Query("select b from Task b where b.technician.id=:id")
	Collection<Task> findTaskByTechnicianId(int id);

	//encontrar el record con el tecnico
	@Query("select b from MaintanenceRecord b where b.technician.id=:id")
	Collection<MaintanenceRecord> findRecordByTechnicianId(int id);

	//CAMBIADO
	@Query("SELECT COUNT(b) > 0 FROM InvolvedIn b WHERE b.maintanenceRecord = :maintanenceRecord AND b.task = :task")
	boolean existsByRecordAndTask(@Param("maintanenceRecord") MaintanenceRecord maintanenceRecord, @Param("task") Task task);

	//CAMBIADO
	@Query("select b from MaintanenceRecord b where b.technician.id=:id and b.draftMode=:draftMode")
	Collection<MaintanenceRecord> findNotPublishRecord(@Param("id") int id, @Param("draftMode") boolean draftMode);

	@Query("SELECT b FROM InvolvedIn b WHERE b.maintanenceRecord = :maintanenceRecord AND b.task = :task")
	Collection<InvolvedIn> findByRecordAndTask(MaintanenceRecord maintanenceRecord, Task task);

	//si el draftmode en falso = publicado
	//en true es unpublished

}
