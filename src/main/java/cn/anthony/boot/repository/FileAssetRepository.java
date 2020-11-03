package cn.anthony.boot.repository;

import cn.anthony.boot.domain.FileAsset;
import cn.anthony.boot.domain.QFileAsset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FileAssetRepository
		extends BaseRepository<FileAsset, QFileAsset,String> {
//	@Override
//	default public void customize(QuerydslBindings bindings, QFileAsset p) {
//		bindings.bind(String.class).first(new SingleValueBinding<StringPath, String>() {
//			@Override
//			public Predicate bind(StringPath path, String value) {
//				return path.containsIgnoreCase(value);
//			}
//		});
//	}

	@Query("{ $or : [ { $where: '?0 == null' } , { field : ?0 } ] }")
	List<FileAsset> findAll(String query);

	@Query("{ $or : [ { $where: '?0.length == 0' } , { field : { $in : ?0 } } ] }")
	List<FileAsset> findAllIn(String query, Pageable pageable);
	
	@Query("{nr:{$ne:null}}")
	Page<FileAsset> findWithAsset(Pageable pageable);

	List<FileAsset> findByNr(String nr);

}
