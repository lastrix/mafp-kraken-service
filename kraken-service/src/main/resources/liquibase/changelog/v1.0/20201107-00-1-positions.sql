DO
$$
    DECLARE
        id bigint;
    BEGIN
        FOR _ in 1..1000
            LOOP
                id = nextval('kraken_service.position_seq');
                INSERT INTO kraken_service.position(position_id, name, description)
                VALUES (id::regclass, 'Position' || id, 'Position ' || id);
            end loop;
    end;
$$;
