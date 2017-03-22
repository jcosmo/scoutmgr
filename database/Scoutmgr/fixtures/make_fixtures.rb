require 'yaml'
@badge = nil
@description = nil

@categories = {}
@badges = {}
@tasks = {}
@task_stack = []

def new_category(name)
  id = @categories.size + 1
  @current_category = @categories[id] =
    {
      'ID' => id,
      'Name' => name,
      'ScoutSectionID' => 3,
      'Rank' => id
    }
  p "Category #{name}"
end

def new_badge(name, description)
  id = @badges.size + 1
  @current_badge = @badges[id] =
    {
      'ID' => id,
      'Name' => name,
      'Description' => description.strip,
      'BadgeCategoryID' => @current_category['ID'],
      'Rank' => id
    }
  p "Badge #{name}"
end

def new_task(description, parent = nil, new_parent = nil)
  @task_stack = [] unless parent

  id = @tasks.size + 1
  p "Task #{description}"
  @current_task = @tasks[id] =
    {
      'ID' => id,
      'Description' => description,
      'BadgeID' => @current_badge['ID'],
      'Rank' => id
    }
  if parent
    @task_stack.pop unless new_parent
    @current_task['ParentID'] = @task_stack[-1]['ID']
  end
  @task_stack << @current_task
end

File.foreach('greenbook.txt').with_index do |line, line_num|
  line.chomp!.strip!
  if line && !(idx = line.index(':')).nil?
    tag = line[0..idx-1].strip
    content = line[idx+1..-1].strip
    if !@description.nil?
      new_badge(@badge, @description)
      @description = nil
    end
    if 'category' == tag
      new_category(content)
    elsif 'badge' == tag
      @badge = content
      @description = ""
    elsif 'task' == tag
      new_task(content)
    elsif ('a'..'z').include?(tag)
      new_task(content, true, 'a' == tag)
    elsif ('1'..'9').include?(tag)
      new_task(content, true, '1' == tag)
    else
      throw "Unexpected tag #{tag} on line #{line_num}"
    end
  elsif line
    if @description
      @description << line << "\n"
    elsif line.length > 0
      throw "No description on line #{line_num}: #{line.length}" unless @description
    end
  end
end

if @description
  new_badge(@badge, @description)
end

File.open('Scoutmgr.tblBadgeCategory.yml', 'w') { |f| f.write(@categories.to_yaml) }
File.open('Scoutmgr.tblBadge.yml', 'w') { |f| f.write(@badges.to_yaml) }
File.open('Scoutmgr.tblBadgeTask.yml', 'w') { |f| f.write(@tasks.to_yaml) }
