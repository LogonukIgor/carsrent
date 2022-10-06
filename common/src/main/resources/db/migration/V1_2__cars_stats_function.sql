drop function if exists get_cars_stats_number_of_cars_in_stock();

create or replace function get_cars_stats_number_of_cars_in_stock() returns integer
    language sql
as
$$
select count(id)
from cars_rent.cars
where is_deleted = false
  and is_in_stock = true;
$$;

alter function get_cars_stats_number_of_cars_in_stock() owner to postgres;