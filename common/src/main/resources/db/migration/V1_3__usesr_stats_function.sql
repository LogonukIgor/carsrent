drop function if exists get_users_stats_number_of_users(boolean);

create or replace function get_users_stats_number_of_users(is_deleted_param boolean) returns integer
    language sql
as
$$
select count(id)
from cars_rent.users
where is_deleted = is_deleted_param;
$$;

alter function get_users_stats_number_of_users(boolean) owner to postgres;
