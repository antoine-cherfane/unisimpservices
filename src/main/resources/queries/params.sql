USE unisimp;

TRUNCATE `list_parameter`;
TRUNCATE `parameter`;

-- Relation Types
INSERT INTO `list_parameter` (`id_list_parameter`, `name`) VALUES (1, "Relation Types");
INSERT INTO `parameter` (`id_parameter`, `value`, `obj_list_parameter`) VALUES (1, "Friend Request Sent", 1);
INSERT INTO `parameter` (`id_parameter`, `value`, `obj_list_parameter`) VALUES (2, "Friends", 1);
