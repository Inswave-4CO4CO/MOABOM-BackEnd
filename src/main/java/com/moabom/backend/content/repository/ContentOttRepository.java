package com.moabom.backend.content.repository;

import org.springframework.data.jpa.repository.*;

import com.moabom.backend.content.model.*;

import java.util.List;

public interface ContentOttRepository extends JpaRepository<ContentOtt, ContentOttId> {

	@Query(value = """
		    SELECT content_id, title, image, ott_name, release_date
		    FROM (
		        SELECT 
		            c.content_id,
		            c.title,
		            c.image,
		            o.ott_name,
		            c.release_date,
		            ROW_NUMBER() OVER (PARTITION BY c.content_id ORDER BY co.ott_id) AS rn
		        FROM content c
		        JOIN content_ott co ON c.content_id = co.content_id
		        JOIN ott o ON co.ott_id = o.ott_id
		        WHERE c.release_date > CURRENT_DATE
		    ) sub
		    WHERE rn = 1
		    """, nativeQuery = true)
		List<Object[]> findUpcomingUniqueContents();

}

