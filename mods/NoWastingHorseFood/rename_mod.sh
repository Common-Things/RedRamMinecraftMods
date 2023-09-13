#!/bin/sh
# Linux shell script because Java is horrible and I have to repeat the same name 27 times
# In Rust, 90% of the time its just in one place, Cargo.toml.
# Sometimes in main.rs and other main.rs modules also.

# usage: ./rename_mod.sh
# enter prompts.

# shellcheck disable=2154 # read_prompt sets the variables

# remove ./ prefix if present
SELF_FILE_NAME="${0#\./}"

# -p isnt posix
# prompt: $1, var: $2 -> $$2
read_prompt() {
    printf '%s' "$1"
    read -r "${2?}" # to please shellcheck
    # printf '\n'
}

# find: $1, replace: $2, dir: optional<$3>
recursive_find_and_replace() {
    # Get all files, expect this file, with occurences of `find`
    # does not work for files with colon or newlines in the name, or `find` with slashes
    # shellcheck disable=2086 # allow wordsplitting $3, breaks if spaces but expands to NOTHING if blank (wanted)
    files=$(grep -r --binary-files=without-match "$1" $3 | awk -F ':' '{print $1}' | uniq | sed "/$SELF_FILE_NAME/d")
    # disp files that are gonna be changed
    echo "$files"
    # replace `find` with `replace`
    echo "$files" | xargs '-I{}' sed -i -e "s/$1/$2/g" '{}'
}

# TODO: rename files, not just contents

read_prompt "current modid?: " old_mod_id
read_prompt "replace with: " new_mod_id
read_prompt "current ClassName?: " old_class_name
read_prompt "replace with: " new_class_name
read_prompt "current Fabric Mod Name?: " old_fabric_mod_name
read_prompt "replace with: " new_fabric_mod_name

recursive_find_and_replace "$old_mod_id" "$new_mod_id"
recursive_find_and_replace "$old_class_name" "$new_class_name"
recursive_find_and_replace "$old_fabric_mod_name" "$new_fabric_mod_name"