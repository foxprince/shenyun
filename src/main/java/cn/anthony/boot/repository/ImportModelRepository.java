package cn.anthony.boot.repository;

import cn.anthony.boot.domain.ImportModel;
import cn.anthony.boot.domain.QImportModel;

public interface ImportModelRepository extends BaseRepository<ImportModel, QImportModel,String> {
//	@Override
//	default public void customize(QuerydslBindings bindings, QImportModel p) {
//		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
//	}
}
