CREATE TABLE students (
		student_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		last_name VARCHAR(50) NOT NULL,
		first_name VARCHAR(50) NOT NULL,
		email VARCHAR(100) NOT NULL,
		phone VARCHAR(20)
);

CREATE TABLE courses (
		course_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		course_number VARCHAR(20) NOT NULL -- e.g. CS-3354-001
);

CREATE TABLE projects (
		project_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		course_id INTEGER CONSTRAINT projects_fk REFERENCES courses,
		project_name VARCHAR(50) NOT NULL
);

-- keeps track of which students are assigned which projects
CREATE TABLE project_assignments (
		proj_assign_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		project_id INTEGER CONSTRAINT paproj_fk REFERENCES projects,
		student_id INTEGER CONSTRAINT pastudent_fk REFERENCES students
);

-- keeps track of which students are on which teams
CREATE TABLE teams (
		team_member_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		project_id INTEGER CONSTRAINT teams_proj_fk REFERENCES projects,
		student_id INTEGER CONSTRAINT teams_student_fk REFERENCES students,
		team_number INTEGER NOT NULL
);

-- keeps track of which skills belong to which projects
CREATE TABLE skills (
		id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		project_id INTEGER CONSTRAINT skill_proj_fk REFERENCES projects,
		name VARCHAR(100) NOT NULL
);

-- keeps track of a students ratings for a particular skill on a particular project
CREATE TABLE ratings (
		id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
		skill_id INTEGER CONSTRAINT rate_skill_fk REFERENCES skills,
		student_id INTEGER CONSTRAINT rate_student_fk REFERENCES students,
		rating INTEGER NOT NULL
);

