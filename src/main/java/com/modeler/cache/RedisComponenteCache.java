package com.modeler.cache;

import com.modeler.model.Componente;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RedisComponenteCache {

    private HashOperations hashOperations;

    private RedisTemplate redisTemplate;

    public RedisComponenteCache(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public void saveComponent(Componente componente){
        hashOperations.put("COMPONENTE", componente.getId(), componente);

    }
    public Componente findById(int idComponente){
        return (Componente)hashOperations.get("COMPONENTE",idComponente);
    }
    public void deleteComponent(Componente componente){
        hashOperations.delete("COMPONENTE",componente.getId());
    }
    public List<Componente>findAll(){
        System.out.println(hashOperations.values("COMPONENTE"));
        return hashOperations.values("COMPONENTE");
    }
}
